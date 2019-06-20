import yaml

class ConfigParser:
    def __init__(self, filename):
        try:
            with open(filename, 'r') as ymlfile:
                cfg = yaml.load(ymlfile, Loader=yaml.FullLoader)
                self.cfg = cfg
        except FileNotFoundError:
            print("Can't find or open file")
            self.cfg = {}

    def get_address(self, cfg):
        if cfg["config"]["address"]:
            return cfg["config"]["address"]
        else:
            return "0.0.0.0"

    def get_port(self, cfg):
        if cfg["config"]["port"]:
            return cfg["config"]["port"]
        else:
            return "8080"
