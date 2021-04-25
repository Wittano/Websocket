import json
import asyncio
from channels.generic.websocket import AsyncWebsocketConsumer
from asgiref.sync import sync_to_async
from apps.message.models import Message
from django.contrib.auth.models import User
from apps.message.serializers import MessageSerializer


class PublicChatConsumer(AsyncWebsocketConsumer):
    _group_name = 'chat-public'

    async def connect(self):
        await self.channel_layer.group_add(self._group_name, self.channel_name)

        await self.accept()

    async def disconnect(self, close_code: int):
        await self.channel_layer.group_discard(self._group_name, self.channel_name)

    async def receive(self, text_data: str):
        message = Message(message=text_data, sender=self.scope['user'])

        await sync_to_async(message.save)()

        await self.channel_layer.group_send(self._group_name, {
            'type': 'send_message',
            'message': MessageSerializer(message).data
        })

    async def send_message(self, event):
        await self.send(text_data=json.dumps(event['message']))


class PrivateChatConsumer(AsyncWebsocketConsumer):
    async def _get_user(self, name: str) -> User:
        return await sync_to_async(User.objects.get)(username=name)

    async def connect(self):
        self._sender = self.scope['url_route']['kwargs']['from']
        self._receiver = self.scope['url_route']['kwargs']['to']
        self._groups = [f'chat-{self._sender}-{self._receiver}',
                        f'chat-{self._receiver}-{self._sender}']

        for group in self._groups:
            await self.channel_layer.group_add(group, self.channel_name)

        await self.accept()

    async def disconnect(self, close_code: int):
        for group in self._groups:
            await self.channel_layer.group_discard(group, self.channel_name)

    async def receive(self, text_data: str):
        message = Message(message=text_data, sender=await self._get_user(
            self._sender), receiver=await self._get_user(self._receiver))

        await sync_to_async(message.save)()

        for group in self._groups:
            await self.channel_layer.group_send(group, {
                'type': 'send_message',
                'message': MessageSerializer(message).data
            })

    async def send_message(self, event):
        await self.send(json.dumps(event['message']))
