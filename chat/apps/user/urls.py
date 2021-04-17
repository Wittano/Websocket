from . import views
from django.urls import path

urlpatterns = [
    path("", views.UserCreateView.as_view(), name="user-create")
]
