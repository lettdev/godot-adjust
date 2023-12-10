extends Node2D

func _ready():
	$Button.pressed.connect(Adjust._on_Button_pressed)
