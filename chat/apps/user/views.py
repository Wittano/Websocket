from django.shortcuts import render
from rest_framework.generics import CreateAPIView, ListAPIView
from .serializers import UserSerializer, PublicUserSerizlizer
from rest_framework import permissions
from django.contrib.auth.models import User

class UserCreateView(CreateAPIView):
    serializer_class = UserSerializer
    permission_classes = [permissions.AllowAny]


class UserListView(ListAPIView):
    permission_classes = [permissions.IsAuthenticated]
    serializer_class = PublicUserSerizlizer

    def get_queryset(self):
        queryset = User.objects.exclude(id=self.request.user.pk)

        return queryset
    
class UsernameView(ListAPIView):
    permission_classes = [permissions.IsAuthenticated]
    serializer_class = PublicUserSerizlizer

    def get_queryset(self):
        return User.objects.filter(id=self.request.user.id)
    