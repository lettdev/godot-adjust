extends Node

var _plugin_name = "GodotAdjust"
var _android_plugin

var adjust_app_token : String
var adjust_production : bool

func _ready():
	adjust_app_token = ProjectSettings.get_setting("krad/adjust_sdk/general/app_token", "")
	adjust_production = ProjectSettings.get_setting("krad/adjust_sdk/general/production_mode", false)
	
	if Engine.has_singleton(_plugin_name):
		print_rich("[color=green]" + _plugin_name + " has successfully loaded![/color]")
		if adjust_production: print_rich("[color=green]MODE: PRODUCTION[/color]")
		else: print_rich("[color=yellow]MODE: SANDBOX[/color]")
		_android_plugin = Engine.get_singleton(_plugin_name)
		_android_plugin.init(adjust_app_token, adjust_production)
	else:
		printerr("Couldn't find plugin " + _plugin_name)
