# Generated by Django 3.2 on 2021-04-20 21:08

from django.db import migrations, models
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        ('message', '0007_alter_message_date'),
    ]

    operations = [
        migrations.AlterField(
            model_name='message',
            name='date',
            field=models.DateTimeField(default=django.utils.timezone.now),
        ),
    ]
