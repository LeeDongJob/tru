package com.screen.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapTiled {
	TiledMap map;
	private float tileWidth;
	private float tileHeight;
	public MapTiled(String name){
		map = new TmxMapLoader().load(name);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		tileWidth = layer.getTileWidth();
	    tileHeight = layer.getTileHeight();
	}
	public TiledMap getMap() {
		return map;
	}
	public float getTileHeight() {
		return tileHeight;
	}
	public void setTileHeight(float tileHeight) {
		this.tileHeight = tileHeight;
	}
	public float getTileWidth() {
		return tileWidth;
	}
	public void setTileWidth(float tileWidth) {
		this.tileWidth = tileWidth;
	}
}
