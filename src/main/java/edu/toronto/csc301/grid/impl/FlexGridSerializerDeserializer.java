package edu.toronto.csc301.grid.impl;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.function.Function;
import java.util.Iterator;
import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;
import edu.toronto.csc301.warehouse.Rack;


public class FlexGridSerializerDeserializer implements IGridSerializerDeserializer {
	public FlexGridSerializerDeserializer(){
	}

	@Override
	public <T> void serialize(IGrid<T> grid, OutputStream output,
		      Function<T, byte[]> item2byteArray) throws IOException {
		    // TODO Auto-generated method stub
		    
		    //OutputStreamWriter writer = new OutputStreamWriter(output);

		    
		    Iterator<GridCell> cellIterator = grid.getGridCells();
		    
		    GridCell gridBlock; 
		    while (cellIterator.hasNext()) {
		      gridBlock = cellIterator.next(); 
		      if(grid.getItem(gridBlock) != null){
		        output.write ((gridBlock.x + ":" + gridBlock.y + " ").getBytes());
		        output.write ((item2byteArray.apply(grid.getItem(gridBlock))));
		        output.write("\n".getBytes());
		      }
		      else{
		        output.write ((gridBlock.x + ":" + gridBlock.y + "\n").getBytes()); 
		        }
		    }
 
	}

	@Override
	public <T> IGrid<T> deserialize(InputStream input, Function<byte[], T> byteArray2item) throws IOException {
		//Read line by line
		//declare flexgrid and hashmap variables to store data
		//need a bufferred reader object
		//all data in string form,cast to int 
		FlexGrid<T> grid = new FlexGrid<T>();
		HashMap<GridCell,Rack> cellMap = grid.getCellMap();
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		int cell_x,cell_y;
		String line;
		String[]lines;//store characters of each line
		String[]number;//store the x and y value
		while((line=br.readLine())!=null){
			lines = line.split(" ");
			number = lines[0].split(":");//coordinates
			cell_x = Integer.parseInt(number[0]);
			cell_y = Integer.parseInt(number[1]);
			if (lines.length == 2 ){
		        cellMap.put(GridCell.at(cell_x, cell_y), (Rack) byteArray2item.apply(lines[1].getBytes())); 
		    }else{
		        cellMap.put(GridCell.at(cell_x, cell_y), null);
		     }
		  }
		 return grid;
	}
			
			
			
}
