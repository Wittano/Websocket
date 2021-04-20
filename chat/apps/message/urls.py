from django.urls import path, re_path
from . import views

urlpatterns = [
    path('', views.MessageListView.as_view()),
    re_path(r'^(?P<username>\w+)/', views.PrivateMessageListView.as_view())
]
