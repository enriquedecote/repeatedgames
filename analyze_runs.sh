#!/bin/bash

# ################################################################# #
# Compute the mobile average of a numerical series obtained as the  #
# average of a set of numerical series in the specified directory   #
# ################################################################# #

# ################ #
# Input Parameters #
# ################ #

# Directory that contains all the numerical series files
dir=$1

# Number of the column to consider in averaging
col=$2

# First line in the file
first_row=$3

# Number of sample in the numerical series
length=$4

# Name of the output file
outfile=$5

# Size of the fixed window used to compute the mobile average
window=$6

# Name of the octave script file
script="oct_script.m"

# ########## #
# Script     #
# ########## #

echo "Directory  = "$dir
echo "Column     = "$col
echo "First Row  = "$first_row
echo "Length     = "$length
echo "Outfile    = "$outfile
echo "Window     = "$window

let last_col=$col

# Create the script
echo "" > $script

# Move into the directory
echo "cd "$dir >> $script

# Read all the files in the directory
echo "files = readdir('.');" >> $script

# Store the number of files in the directory
echo "num_files = rows(files);" >> $script

# Since the first two files are "." and "..", start from the third
# Import the series corresponding to the specified column
echo "tmp = dlmread(files{3},\"\t\",["$first_row","$col","$length","$last_col"]);" >> $script

# The matrix containing all the series
echo "m = tmp;" >> $script

# Loop on the file in the directory
echo "if (num_files > 3)" >> $script
echo "for i = 4:num_files;" >> $script
echo "files{i};" >> $script
echo "tmp = dlmread(files{i},\"\t\",["$first_row","$col","$length","$last_col"]);" >> $script
echo "m = [m tmp];" >> $script
echo "endfor;" >> $script
# Compute the average and the standard deviation
echo "result = mean(m')';" >> $script
echo "result = [result std(m')'];"  >> $script

echo "w = "$window";" >> $script
echo "n = size(result,1);" >> $script

echo "only_mean = result*[1 0]';" >> $script
echo "z_mean = [0 cumsum(only_mean')];" >> $script
echo "y_mean = ( z_mean(w+1:n+1) - z_mean(1:n-w+1) )/w;" >> $script

echo "only_std = result*[0 1]';" >> $script
echo "z_std = [0 cumsum(only_std')/(num_files-2)];" >> $script
echo "y_std = ( z_std(w+1:n+1) - z_std(1:n-w+1) )/w;" >> $script

echo "final_res = [y_mean' y_std'];" >> $script
echo "else">> $script
# Compute the average and the standard deviation
echo "result = m;" >> $script

echo "w = "$window";" >> $script
echo "n = size(result,1);" >> $script

echo "only_mean = result;" >> $script
echo "z_mean = [0 cumsum(only_mean')];" >> $script
echo "y_mean = ( z_mean(w+1:n+1) - z_mean(1:n-w+1) )/w;" >> $script

echo "final_res = [y_mean'];" >> $script
echo "endif" >> $script


# Write the result on the output file
echo "dlmwrite(\""$outfile"\",final_res,'\t');" >> $script

# Launch the octave script
octave -qf $script

#rm -f $script