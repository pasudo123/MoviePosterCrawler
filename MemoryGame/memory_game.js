
var memory_array = ['A', 'A', 'B', 'B', 'C', 'C', 'D', 'D', 'E', 'E', 'F', 'F', 'G', 'G', 'H', 'H', 'I', 'I', 'J', 'J', 'K', 'K', 'L', 'L'];
var memory_values = [];
var memory_tile_ids = [];
var tiles_fliped = 0;

Array.prototype.memory_tile_shuffle = function(){
    var i = this.length, j, temp;

    console.log('i :: ' + i);

    while(--i > 0){
        j = Math.floor(Math.random() * (i + 1));
        temp = this[j];
        this[j] = this[i];
        this[i] = temp;
    }
}

function newBoard(){

    tiles_fliped = 0;
    var output = '';

    memory_array.memory_tile_shuffle();
    var length = memory_array.length;
    for(var i = 0; i < length; i++){
        output += '<div id = "tile_' + i + '" onclick = "memoryFlipTile(this, \''+ memory_array[i] + '\')"></div>';
    }

    document.getElementById('memory_board').innerHTML = output;
}

function memoryFlipTile(tile, val){
    
    if(tile.innerHTML == "" && memory_values.length < 2){
        tile.style.background = '#FFF';
        tile.innerHTML = val;

        if(memory_values.length == 0){
            memory_values.push(val);
            memory_tile_ids.push(tile.id);
        }
        else if(memory_values.length == 1){
            memory_values.push(val);
            memory_tile_ids.push(tile.id);

            // 일치
            if(memory_values[0] == memory_values[1]){
                tiles_fliped += 2;

                // Clear Both Arrays
                memory_values = [];
                memory_tile_ids = [];

                if(tiles_fliped == memory_array.length){
                    alert("Board Clear !! Generating New Board");
                    document.getElementById('memory_board').innerHTML = "";
                    newBoard();
                }
            }

            // 불일치
            else{
                function flip2Back(){

                    // Flip the 2 tiles
                    var tile_1 = document.getElementById(memory_tile_ids[0]);
                    var tile_2 = document.getElementById(memory_tile_ids[1]);
                    
                    tile_1.style.background = 'E:\doubler\poster\007 골드핑거 (1964).png no-repeat';
                    tile_1.innerHTML = "";

                    tile_2.style.background = 'E:\doubler\poster\007 골드핑거 (1964).png no-repeat';
                    tile_2.innerHTML = "";

                    // Clear Both Arrays
                    memory_values = [];
                    memory_tile_ids = [];
                }

                setTimeout(flip2Back, 700);
            }
        }
    }
}