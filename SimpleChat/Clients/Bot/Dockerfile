FROM python:3

WORKDIR /usr/src/app

RUN pip install requests
RUN pip install python-socketio

COPY HelloBot.py ./Clients/Bot/
COPY Bot.py .

CMD [ "python", "./Bot.py"]