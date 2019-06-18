FROM python:slim

WORKDIR /usr/src/app

RUN pip install requests
RUN pip install python-socketio

COPY Qwickpot.py ./Clients/Bot/
COPY Bot.py .

CMD [ "python", "./Bot.py"]