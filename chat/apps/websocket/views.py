import json
import asyncio
from channels.generic.websocket import AsyncJsonWebsocketConsumer
from asgiref.sync import sync_to_async
from apps.message.models import Message
from apps.message.serializers import MessageSerializer


class PublicChatConsumer(AsyncJsonWebsocketConsumer):
    async def receive(self, text_data: str):
        message = Message(message=text_data, sender=self.scope['user'])

        await sync_to_async(message.save)()

        asyncio.ensure_future(
            self.send(json.dumps(MessageSerializer(message).data)))


class PrivateChatConsumer(AsyncJsonWebsocketConsumer):
    async def receive(self, text_data: str):
        asyncio.ensure_future(sync_to_async(
            Message.objects.create)(json.loads(text_data)))

        await self.send(json.dumps({
            'data': text_data,
            'from': self.scope['url_route']['kwargs']['from'],
            'to': self.scope['url_route']['kwargs']['to']
        }))
