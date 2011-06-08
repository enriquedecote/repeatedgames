#!/bin/bash
# $1 is the xml file experiment (must be without extension)
# $2 is the number of steps in the numerical series
# $3 is the column to analize: 0,1 (actions), 2,3 (rewards)
# $4 is the experiment number

EXEC=~/experiments/repeatedgames/tmp
LOGGER=rewardLogger--
RESULTS=~/experiments/repeatedgames

if [ $# -lt 1 ]
then
    echo "First parameter --> name of the xml file";
    exit 1;
fi;

cd $EXEC;


mkdir $RESULTS/$1_$4;
mv $1_$LOGGER*.log $RESULTS/$1_$4;

echo "Statistical computation..."
cd $RESULTS;

#$1=Directory that contains all the numerical series files
#$2=Number of the column to consider in averaging (2 for first agent)
#$3 First line in the file
#$4 Number of sample in the numerical series
#$5 Name of the output file
#$6 Size of the fixed window used to compute the mobile average
# Name of the octave script file
rm $RESULTS/.DS_Store
$RESULTS/analyze_runs.sh $RESULTS/$1_$4 $3 0 $2 $1_$4.mob 100;
#rm -f oct_script.m;

mv $EXEC/*.log $RESULTS/$1_$4;

echo "Generating plot...";
cd $RESULTS/$1_$4;
# 1. File to plot
# 2. Graph title
# 3. Every
# 4. Optimal baseline
../2_gen_graphs.sh $1_$4 $1 100 3;

#echo "Compressing files...";
#cd $RESULTS;
#tar czf $PWD/$1.tgz $1/;

echo "Job finished!";
exit 0;
