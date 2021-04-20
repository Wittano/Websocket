from django.db import close_old_connections
from django.contrib.auth import get_user_model
from urllib.parse import parse_qs
from rest_framework_simplejwt.tokens import UntypedToken
from rest_framework_simplejwt.exceptions import InvalidToken, TokenError
from chat.settings import SECRET_KEY
from asgiref.sync import sync_to_async
import logging
import jwt


class TokenAuthMiddleware():
    def __init__(self, apps):
        self.apps = apps

    async def __call__(self, scope, receive, send):
        close_old_connections()

        try:
            token = parse_qs(scope['query_string'].decode('utf-8'))['token'][0]
        except:
            logging.warn("Token wasn't found in query string")
            return None

        try:
            UntypedToken(token)
        except (TokenError, InvalidToken) as e:
            logging.error(e)
            return None
        else:
            decode = jwt.decode(token, SECRET_KEY, algorithms=['HS256'])
            scope['user'] = await sync_to_async(get_user_model().objects.get)(id=decode['user_id'])

        return await self.apps(scope, receive, send)
