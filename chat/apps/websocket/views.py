from django.shortcuts import render
from channels.generic.websocket import AsyncJsonWebsocketConsumer
from django.contrib.auth import get_user_model
from asgiref.sync import sync_to_async
import json


class PublicChatConsumer(AsyncJsonWebsocketConsumer):
    async def receive(self, text_data: str):
        await self.send(text_data)


class PrivateChatConsumer(AsyncJsonWebsocketConsumer):
    async def receive(self, text_data: str):
        sync_to_async(get_user_model().objects.create)(json.loads(text_data))

        await self.send(json.dumps({
            'data': text_data,
            'from': self.scope['url_route']['kwargs']['from'],
            'to': self.scope['url_route']['kwargs']['to']
        }))
