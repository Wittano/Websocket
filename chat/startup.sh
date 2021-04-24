#!/bin/bash
echo "Migrating database before starting Django"

python manage.py makemigrations
python manage.py migrate

echo "Starting server"
python manage.py runserver 0.0.0.0:8000