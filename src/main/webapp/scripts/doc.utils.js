var fileCount = 1;
var imageCount = 1;
var max = 10;
var evenRowBgColor = '#a9a9a9';
var oddRowBgColor = 'white';

function addImage(){
	if(imageCount < max){
		imageCount++;	
		var row = createNewRow('files', imageCount + fileCount);		
		
		addCell(row, 'Image');
		addCell(row, getFileInput('image', imageCount));	
		addCell(row, 'Title');
		addCell(row, getTextInput('title', imageCount));
		/*addCell(row, 'Caption');
		adCell(row, getTextInput('caption', imageCount));*/	
	}
	return false;
}

/*
function addDocument(){
	if(fileCount < max){
		fileCount++;	
		var row = createNewRow('files', fileCount + imageCount);		
		
		addCell(row, 'Document');
		addCell(row, getFileInput('file', fileCount));	
		addCell(row, 'Title');
		addCell(row, getTextInput('title', fileCount, null, 20));
		addCell(row, 'Description');
		addCell(row, getTextInput('description', fileCount, null, 20));		
	}
	return false;
}*/
function getFileInput(name, count){
	return '<input type="file" id="' + name + count + '" name="' + name + count + '" />';	
}

function getTextInput(name, count, value, size){
	if(!size){
		size = 25;
	}
	if(!value){
		value = '';
	}
	return '<input type="text" id="' + name + count + '" name="' + name + count + '" value="' + value + '" size="' + size + '" class="required-input"/>';	
}

function createNewRow(tableId, count){	
	var table = document.getElementById(tableId);
	var tr = table.insertRow(table.rows.length);
	decorateTable(table);	
	
	return tr;
}

function decorateTable(table){
	for(i = 1; i < table.rows.length; i++){
		if(i % 2 == 0){
			table.rows[i].style.backgroundColor = evenRowBgColor;
		}else{
			table.rows[i].style.backgroundColor = oddRowBgColor;
		}
	}
}


function addCell(row, cellContents){
	var cell = row.insertCell(row.cells.length);
	cell.innerHTML = cellContents;		
}