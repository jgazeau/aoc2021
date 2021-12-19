#!/bin/bash
IFS=$'\n'
dayNumber=1
dayLabel="DAY${dayNumber}"
scriptDir="$(dirname "$0")"
inputName="input${dayNumber}"
inputArray=($(cat ${scriptDir}/${inputName}))
incrementCount=-1
tempValue=0
for line in ${inputArray[@]} ;do
  if [ ${line} -gt ${tempValue} ];then
    incrementCount=$((incrementCount+1))
  fi
  tempValue=${line}
done
printf "%s%s\n" "${dayLabel}-puzzle1 --> Results: ${incrementCount}"

incrementCount=-1
tempValue=0
shiftSize=3
for ((i=0;i<(${#inputArray[@]}-${shiftSize}+1);i++)) ;do
  tempShiftValue=0
  for ((j=0;j<${shiftSize};j++)) ;do
    tempShiftValue=$((tempShiftValue+${inputArray[i+j]}))
  done
  if [ ${tempShiftValue} -gt ${tempValue} ];then
    incrementCount=$((incrementCount+1))
  fi
  tempValue=${tempShiftValue}
done
printf "%s%s\n" "${dayLabel}-puzzle2 --> Results: ${incrementCount}"