from django.shortcuts import render
from django.contrib.auth import get_user_model
from rest_framework.generics import ListAPIView
from apps.message.models import Message
from .serializers import MessageSerializer


class MessageListView(ListAPIView):
    serializer_class = MessageSerializer
    queryset = Message.objects.filter(receiver=None)


class PrivateMessageListView(ListAPIView):
    serializer_class = MessageSerializer

    def get_queryset(self):
        user = self.request.user
        user2 = get_user_model().objects.get(username=self.kwargs['username'])

        send_message = Message.objects.filter(sender=user, receiver=user2)
        receive_message = Message.objects.filter(sender=user2, receiver=user)

        return send_message.union(receive_message)
