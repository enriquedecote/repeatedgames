#!/bin/sh
file=graphs$1.gplot

# 1. File to plot
# 2. Graph title
# 3. Every
# 4. Optimal baseline

echo "set terminal postscript enhanced eps color solid \"Helvetica\" 10" > $file
echo "set output \"Graph$1.eps\"" >> $file
echo "set title \"$2\"" >> $file
echo "set data style linespoints" >> $file
echo "set border 3" >> $file
echo "set xtics nomirror" >> $file
echo "set ytics nomirror" >> $file
echo "set multiplot" >> $file
echo "set key bottom box" >> $file

#echo "set xrange [0:100]"
echo "set xlabel \"Training Episodes\"" >> $file

#echo "set yrange [0:1.1]" >> $file
echo "set ylabel \"Average Payoffs\"" >> $file

results=`ls *.mob`
plotcmd="plot "

for x in $results
do
plotcmd="$plotcmd\"$x\" using (\$1),"
done
echo ${plotcmd%?} >> $file

gnuplot $file