from .models import Message
from rest_framework import serializers
from django.contrib.auth import get_user_model
from apps.user.serializers import UserSerializer


from datetime import datetime

class MessageSerializer(serializers.ModelSerializer):
    sender = serializers.PrimaryKeyRelatedField(read_only=True)
    receiver = serializers.PrimaryKeyRelatedField(read_only=True)

    class Meta():
        model = Message
        fields = '__all__'
