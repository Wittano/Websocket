from django.shortcuts import render
from rest_framework.generics import CreateAPIView
from .serializers import UserSerializer
from rest_framework import permissions


class UserCreateView(CreateAPIView):
    serializer_class = UserSerializer
    permission_classes = [permissions.AllowAny]
