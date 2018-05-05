package edu.toronto.csc301.grid.impl;

import java.util.Iterator;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.warehouse.Rack;

import java.util.Collection;
import java.util.HashMap;

public class RectangularGrid<T> implements IGrid<T> {
	private int x;//height
	private int y;//width
	GridCell grid;
	HashMap <GridCell, Rack> cellMap;

	public RectangularGrid(int y , int x, GridCell grid){
		//check for valid height and width
		if(x <=0 || y<=0){
			throw new IllegalArgumentException();
		}
		//check for valid gridCell
		if(grid == null){
			throw new NullPointerException();
		}
		this.x = x;//height
		this.y = y;//width
		this.grid = grid;
		this.cellMap=new HashMap<GridCell,Rack>();
		//put in the key value pairs in the cellMap
		for(int w=grid.x;w<this.y + grid.x;w++){
			for(int h=grid.y;h< this.x + grid.y;h++){
				this.cellMap.put(GridCell.at(w, h), null);
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getItem(GridCell cell) {
		//check if the cell coordinates are valid
		if(cell.x >= this.y+this.grid.x || cell.y>= this.x + this.grid.y || cell.y<this.grid.y||
				cell.x<this.grid.x){
		     throw new IllegalArgumentException();
		}
		return (T)this.cellMap.get(cell);
	}

	@Override
	public Iterator<GridCell> getGridCells() {
		Collection<GridCell>c = this.cellMap.keySet();
		Iterator<GridCell> cells = c.iterator();
		return cells;
	}

	@Override
	public boolean hasCell(GridCell cell) {
		if( this.cellMap.containsKey(cell)){
			return true;
		}
		else{
			return false;
		}
		
	}
	public HashMap <GridCell,Rack> getCellMap(){
		return this.cellMap;
	}

}
