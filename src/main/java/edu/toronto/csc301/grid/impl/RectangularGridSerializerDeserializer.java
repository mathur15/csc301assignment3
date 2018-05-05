package edu.toronto.csc301.grid.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;

import java.util.function.Function;

import edu.toronto.csc301.grid.GridCell;
import edu.toronto.csc301.grid.IGrid;
import edu.toronto.csc301.grid.IGridSerializerDeserializer;
import edu.toronto.csc301.warehouse.Rack;

public class RectangularGridSerializerDeserializer implements IGridSerializerDeserializer {

	public RectangularGridSerializerDeserializer() {
	    // TODO Auto-generated constructor stub
	  }

	  @Override
	  public <T> void serialize(IGrid<T> grid, OutputStream output,
	      Function<T, byte[]> item2byteArray) throws IOException {
	    
	    int h = 0;
	    int w = 0;
	    
	    int minX = Integer.MAX_VALUE;
	    int minY = Integer.MAX_VALUE;
	    int maxX = Integer.MIN_VALUE;
	    int maxY = Integer.MIN_VALUE;
	    String str = "";
	    Iterator<GridCell> cellIterator = grid.getGridCells();
	    GridCell gridBlock; 
	    while(cellIterator.hasNext()){
	      gridBlock = cellIterator.next(); 
	      if (gridBlock.x < minX )
	        minX = gridBlock.x;
	      if(gridBlock.x> maxX)
	        maxX = gridBlock.x; 
	      if (gridBlock.y < minY )
	        minY = gridBlock.y;
	      if(gridBlock.y> maxY)
	        maxY = gridBlock.y; 
	       
	    }
	    w = maxX-minX+1;
	    h = maxY-minY+1; 
	    
	    for(int x = minX; x<maxX; x++){
	      for (int y = minY; y<maxY; y++){
	          if (!grid.hasCell(GridCell.at(x, y)))
	            throw new IllegalArgumentException(); 
	      }
	    }
	    
	     str = str + (String.format("width: %d\n", w))
	        +(String.format("height: %d\n", h))
	        +(String.format("south-west: %d:%d\n", minX,minY));
	      
	    
	    output.write(str.getBytes());
	   cellIterator = grid.getGridCells();
	 

	    while (cellIterator.hasNext()) {
	      gridBlock = cellIterator.next(); 

	       if(grid.getItem(gridBlock) != null){
	         output.write ((gridBlock.x + ":" + gridBlock.y + " ").getBytes());
	         output.write((item2byteArray.apply((T)grid.getItem(gridBlock))));
	         output.write(("\n").getBytes());
	       }
	    }
	    
	  }

	  @Override
	  public <T> IGrid<T> deserialize(InputStream input,
	      Function<byte[], T> byteArray2item) throws IOException {
		  
		  		//need a bufferedreader object to read line by line
			    BufferedReader reader = new BufferedReader(new InputStreamReader(input)); 
			    String line; 
			    
			    line = reader.readLine();
			    //parsing width,height,gridcell information
			    int width  = Integer.parseInt(line.substring(7));
			    
			    
			    line = reader.readLine();
			    int height = Integer.parseInt(line.substring(8));
			    
			    line = reader.readLine();
			    String[] lines = line.split(":"); 
			    int x = Integer.parseInt(lines[1].substring(1));
			    int y = Integer.parseInt(lines[2]); 
			    
			    RectangularGrid<T> newGrid = new RectangularGrid<T>(width, height, GridCell.at(x, y)); 
			    HashMap <GridCell, Rack> cellMap = newGrid.getCellMap(); 
			    
			    int cellX;
			    int cellY;
			    
			    while ((line = reader.readLine()) != null) {
			      lines = line.split(":"); 
			      cellX = Integer.parseInt(lines[0]); 
			      cellY = Integer.parseInt(lines[1].substring(0, lines[1].length()-2));
			      lines = line.split(" ");
			      cellMap.put(GridCell.at(cellX, cellY), (Rack) byteArray2item.apply(lines[1].getBytes())); 
			      System.out.println("item");
			  }
			   return newGrid; 
	  }

	}
