import socketio

import Clients.Bot.Qwickpot as Quickpot
from ConfigParser import ConfigParser

sio = socketio.Client()
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
    sio.emit('bot_send', bot.trigger_bot(message))


def create_message(message):
    print("Input: {} \n".format(message))
    message["message"] = bot.trigger_bot(message)
    print("Output: {} \n".format(message["message"]))
    return message


configurator = ConfigParser("bot-config.yml")
config = configurator.get_config()

sio.connect('http://' + str(config["config"]["address"]) + ":" + str(config["config"]["port"]))
sio.wait()
