from . import views
from django.urls import path

urlpatterns = [
    path("", views.UserCreateView.as_view(), name='user-create'),
    path("list", views.UserListView.as_view(), name='user-list'),
    path('username/', views.UsernameView.as_view(), name='username')
]
