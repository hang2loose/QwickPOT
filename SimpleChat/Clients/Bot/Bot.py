import json

import socketio

import Clients.Bot.Qwickpot as Quickpot

sio = socketio.Client()
# bot = HelloBot.HelloBot("data-service")
bot = Quickpot.Qwickpot("q")



@sio.event
def connect():
  print('connection established')


@sio.event
def my_message(data):
  print('message received with ', data)
  sio.emit('my response', {'response': 'my response'})


@sio.event
def disconnect():
  print('disconnected from server')


@sio.on('bot_receive')
def handle_bot(message):
  create_message(message)
  sio.emit('bot_send', message)


def create_message(message):
  message["username"] = "🤖 Bot"
  message["message"] = bot.trigger_bot(message["load"]["question"])
  return message


sio.connect('http://localhost:8080')
sio.wait()
