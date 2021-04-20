from django.urls import path, re_path
from . import views

ws_urlpatterns = [
    path('chat', views.PublicChatConsumer.as_asgi()),
    re_path(r'^chat/(?P<from>\w+)/(?P<to>\w+)', views.PrivateChatConsumer.as_asgi())
]
