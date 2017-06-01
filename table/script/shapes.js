var dataRect = [7,16,14,8];
//Initialize d3 presets
var margin = {top: 80, right: 80, bottom: 80, left: 80};
var width = 600 - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;

var svg = d3.select("body").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
    .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
//creates for each integer in 'dataRect' the respective barchart representation
var chartX = 0;
svg.selectAll("rect")
    .data(dataRect)
    .enter().append("rect")
              .attr("height", function(d,i){
                return d * 15;
              })
              .attr("width","50")
              .attr("x",function(d,i){
                chartX+=60;
                return 60*i;
                })
              .attr("y",function(d,i){
                return 300 - (d*15);
              })
              .attr("fill","pink");
///////generates data area
var dataArray = [25, 26,28 ,32 ,37, 45, 55, 70, 90, 120, 135, 160, 168, 172, 177, 180];
var dataYears= ['2000','2001','2002','2003','2004','2005','2006','2007','2008','2009','2010','2011','2012','2013','2014','2015','2016'];

var charHeight = 250, charWidth = 500;

var area = d3.area()
                .x(function(d,i){ return i*20; })
                .y0(height)
   
svg.append("path").attr("d",area(dataArray));