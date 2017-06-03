var dataArray = [20,25,15,19,23,26,30,33,36,39,41,43]; //Werte für Balken
var dataMonth = ['Jan','Feb','Mar','Apri','Mai','Jun','Jul','Aug','Sep','Oct','Nov','Dez'];//Strings
                //Beschriftung YAchse
var dataYAxis = [50,45,40,35,30,25,20,15,10,5];
           //
var x = d3.scaleOrdinal()
            .domain(dataMonth)
            //Distanz zwischen den XAchseneinträgen (Monatsnamen)
            .range([10,55,95,135,175,215,255,295,335,375,415,455]);
var s =10; //Schritte, Marken auf der Y-Achse
var y = d3.scaleOrdinal()
            .domain(dataYAxis)
            //Distanz zwischen den Werten der der Y-Achse
            .range([5*s,10*s,15*s,20*s,25*s,30*s,35*s,40*s,45*s,50*s,55*s]); 


var xAxis = d3.axisBottom(x);
var yAxis = d3.axisLeft(y);
var yPos = 606;//Change for y positioning of XAxis
var margin = {left:120,right:0,top:55,bottom:0} //For global margin

var svg = d3.select("body").append("svg").attr("height","1000").attr("width","100%");
var chartGroup = svg.append("g").attr("transform","translate("+margin.left+","+margin.top+" )")

chartGroup.selectAll("rect")
            .data(dataArray)
            .enter().append("rect")
                .attr("height",function(d,i){return d*10})//Change BOTH the value of the multiplyer on this line and line 32 for the scaling of the bar
                .attr("width","30")//Breite der Balken
                .attr("fill","pink")//Farbe der Balken
                .attr("x",function(d,i){ return 40*i; })//Abstand zwischen dern Balken (hier 10 pixel)
                .attr("y",function(d,i){ return yPos-(d*10); });
chartGroup.append('g')
        .attr("class","x axis")
        .attr("transform","translate(0, "+ yPos +" )")
        .call(xAxis);

chartGroup.append('g')
        .attr("class","y axis")
        .attr("transform","translate(0, 56 )")
        .call(yAxis);