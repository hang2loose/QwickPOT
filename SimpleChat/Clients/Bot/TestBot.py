from Clients.Bot.Qwickpot import Qwickpot


class TestBot:
    def test_bot(self, bot):
        msg = {'event_type': 'new_user',
               'load': {'username': 'rggr'},
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


bot = Qwickpot("q")
TestBot().test_bot(bot)
