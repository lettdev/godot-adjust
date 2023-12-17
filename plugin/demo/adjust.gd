extends Node

var _plugin_name = "GodotAdjust"
var _android_plugin

const adjust_app_token = "YOUR_APP_TOKEN"
const adjust_production = false

func _ready():
	if Engine.has_singleton(_plugin_name):
		print_rich("[color=green]" + _plugin_name + " has successfully loaded![/color]")
		_android_plugin = Engine.get_singleton(_plugin_name)
		_android_plugin.init(adjust_app_token, adjust_production)
	else:
		printerr("Couldn't find plugin " + _plugin_name)

func _on_Button_pressed():
	if _android_plugin:
		print("He")
		_android_plugin.onButtonPressed()
