@tool
extends EditorPlugin

# A class member to hold the editor export plugin during its lifecycle.
var export_plugin : AndroidExportPlugin
var _plugin_name = "GodotAdjust"

func _enter_tree():
	# Initialization of the plugin goes here.
	export_plugin = AndroidExportPlugin.new()
	add_export_plugin(export_plugin)
	add_autoload_singleton("Adjust", "res://addons/" + _plugin_name + "/adjust.gd")


func _exit_tree():
	# Clean-up of the plugin goes here.
	remove_export_plugin(export_plugin)
	export_plugin = null


func _init():
	add_custom_project_setting("krad/adjust_sdk/general/app_token", TYPE_STRING, "")
	add_custom_project_setting("krad/adjust_sdk/general/production_mode", TYPE_BOOL, false)
	var error: int = ProjectSettings.save()
	if error: push_error("Encountered error %d when saving project settings." % error)


class AndroidExportPlugin extends EditorExportPlugin:
	var _plugin_name = "GodotAdjust"
	
	func _supports_platform(platform):
		if platform is EditorExportPlatformAndroid:
			return true
		return false

	func _get_android_libraries(platform, debug):
		if debug:
			return PackedStringArray([_plugin_name + "/bin/debug/" + _plugin_name + "-debug.aar"])
		else:
			return PackedStringArray([_plugin_name + "/bin/release/" + _plugin_name + "-release.aar"])
	
	func _get_android_dependencies(platform, debug):
		if debug:
			return PackedStringArray([
				"com.adjust.sdk:adjust-android:4.37.0",
				"com.android.installreferrer:installreferrer:2.2",
				"com.google.android.gms:play-services-ads-identifier:18.0.1"
			])
		else:
			return PackedStringArray([
				"com.adjust.sdk:adjust-android:4.37.0",
				"com.android.installreferrer:installreferrer:2.2",
				"com.google.android.gms:play-services-ads-identifier:18.0.1"
			])

	func _get_name():
		return _plugin_name


func add_custom_project_setting(name: String, type: int, default_value, hint: int = PROPERTY_HINT_NONE, hint_string: String = "") -> void:
	if ProjectSettings.has_setting(name): return

	var setting_info: Dictionary = {
		"name": name,
		"type": type,
		"hint": hint,
		"hint_string": hint_string
	}

	ProjectSettings.set_setting(name, default_value)
	ProjectSettings.add_property_info(setting_info)
	ProjectSettings.set_initial_value(name, default_value)