FROM python:3

WORKDIR /usr/src/app

EXPOSE 8081

RUN pip install requests

COPY HelloBot.py .

CMD [ "python", "./HelloBot.py"]