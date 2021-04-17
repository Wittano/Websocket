from rest_framework import serializers
from django.contrib.auth.models import User
from rest_framework.exceptions import ValidationError
import re


class UserSerializer(serializers.ModelSerializer):
    def create(self, validated_data) -> User:
        user = User(username=validated_data['username'])
        user.set_password(validated_data['password'])
        user.save()

        return user

    def _validate(self, value: str, name: str, regex: str) -> str:
        if re.compile(regex).findall(value) == []:
            raise ValidationError(f'Invalid {name}')

        return value

    def validate_username(self, value: str) -> str:
        if len(value) < 4:
            raise ValidationError('Username is too short')
        elif len(value) > 150:
            raise ValidationError('Username is too long')

        return self._validate(value, 'username', r'^[\w]{4,150}$')

    def validate_password(self, value: str) -> str:
        if len(value) < 8:
            raise ValidationError('Password is too short')

        return self._validate(
            value,
            'password',
            r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$'
        )

    class Meta:
        model = User
        fields = ['username', 'password']
