from SimpleChat.Clients.Bot.Qwickpot import Qwickpot
from SimpleChat.ConfigParser import ConfigParser


class TestBot:
    def test_bot(self, bot):
        msg = {'event_type': 'new_user',
               'load': {'username': 'rggr',
                        'departmentId': '1'
                        },
               'ID': '7c4eca676b884610b5e2321bc2045889'}
        bot.trigger_bot(msg)
        question = ""
        while "kill" != question:
            question = input("-> ")
            msg = {
                "event_type": "question",
                "load": {"username": "chucky", "question": question},
                "ID": '7c4eca676b884610b5e2321bc2045889'
            }
            bot.trigger_bot(msg)
        msg = {
            "ID": "7c4eca676b884610b5e2321bc2045889",
            "event_type": "user_disconnected"
        }
        bot.trigger_bot(msg)


configurator = ConfigParser("qwickpot-config.yml")
config = configurator.get_config()
bot = Qwickpot("q", config)
TestBot().test_bot(bot)
