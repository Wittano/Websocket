from django.core.validators import RegexValidator


class UsernameValidator(RegexValidator):
    regex = r'^[A-Za-z]{4,50}'
    message = 'Invalid username'


class PasswordValidator(RegexValidator):
    regex = r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,300}$'
    message = 'Invalid password'
