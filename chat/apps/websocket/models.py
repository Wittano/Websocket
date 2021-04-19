from django.db import models
from django.contrib.auth import get_user_model
from django.utils.timezone import now


class Message(models.Model):
    message = models.CharField(
        default='', blank=True, unique=False, max_length=1000)
    sender = models.ForeignKey(
        get_user_model(), on_delete=models.DO_NOTHING, related_name='sender')
    receiver = models.ForeignKey(
        get_user_model(), on_delete=models.DO_NOTHING, related_name='receiver')
    data = models.DateField(default=now)
