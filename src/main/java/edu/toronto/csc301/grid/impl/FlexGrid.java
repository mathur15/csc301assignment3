package edu.toronto.csc301.grid.impl;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.warehouse.Rack;
public class FlexGrid<T> implements IGrid<T> {
	
	int width;
	int height;
	GridCell gridSW;
	HashMap <GridCell, Rack> cellMap; 
	
	public FlexGrid(){
		cellMap = new HashMap<GridCell,Rack>();
	}

	@Override
	public T getItem(GridCell cell) {
		// TODO Auto-generated method stub
		if(!this.cellMap.containsKey(cell)){
			throw new IllegalArgumentException();
		}
		return (T) this.cellMap.get(cell);
	}

	@Override
	public Iterator<GridCell> getGridCells() {
		Collection<GridCell>c = this.cellMap.keySet();
		Iterator<GridCell> cells=c.iterator();
		return cells;
	}

	@Override
	public boolean hasCell(GridCell cell) {
		if(this.cellMap.containsKey(cell)){
			return true;
		}
		return false;
	}
	
	public HashMap <GridCell,Rack> getCellMap(){
		return this.cellMap;
	}

}
