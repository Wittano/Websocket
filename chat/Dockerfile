FROM python:3.9-buster

WORKDIR /app

COPY . .

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

RUN pip install pipenv
# Create list of dependences
RUN pipenv lock && pipenv lock --requirements > requirements.txt
# Install all dependences
RUN pip install -r requirements.txt

ENTRYPOINT [ "bash", "/app/startup.sh" ]