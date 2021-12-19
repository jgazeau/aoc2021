#!/bin/bash
IFS=$'\n'
dayNumber=3
dayLabel="DAY${dayNumber}"
scriptDir="$(dirname "$0")"
inputName="input${dayNumber}"
inputArray=($(cat ${scriptDir}/${inputName}))
gammaRate=""
epsilonRate=""
for ((i=0;i<${#inputArray[0]};i++)) ;do
  columnSum=0
  for line in ${inputArray[@]} ;do
    columnSum=$((columnSum+${line:$i:1}))
  done
  if [ ${columnSum} -ge $(printf "%i\n" "$((${#inputArray[@]}/2))") ];then
    gammaRate="${gammaRate}1"
    epsilonRate="${epsilonRate}0"
  else
    gammaRate="${gammaRate}0"
    epsilonRate="${epsilonRate}1"
  fi
done
hexGammaRate=$((2#${gammaRate}))
hexEpsilonRate=$((2#${epsilonRate}))
printf "%s%s\n" "${dayLabel}-puzzle1 --> Results: $((hexGammaRate*hexEpsilonRate)) (gammaRate=${gammaRate}-->${hexGammaRate}|epsilonRate=${epsilonRate}-->${hexEpsilonRate})"

oxygenRate=(${inputArray[@]})
carbonDioxideRate=(${inputArray[@]})
for ((i=0;i<${#inputArray[0]};i++)) ;do
  if [ ${#oxygenRate[@]} -gt 1 ];then
    tempOxygenRate=()
    columnSum=0
    for line in ${oxygenRate[@]} ;do
      columnSum=$((columnSum+${line:$i:1}))
    done
    if $(awk "BEGIN {exit !(${columnSum} >= ${#oxygenRate[@]}/2)}") ;then
      bitRating=1
    else
      bitRating=0
    fi
    for ((j=0;j<${#oxygenRate[@]};j++)) ;do
      if [ ${bitRating} -eq ${oxygenRate[$j]:$i:1} ];then
        tempOxygenRate+=(${oxygenRate[$j]})
      fi
    done
    oxygenRate=(${tempOxygenRate[@]})
  fi
  if [ ${#carbonDioxideRate[@]} -gt 1 ];then
    tempCarbonDioxideRate=()
    columnSum=0
    for line in ${carbonDioxideRate[@]} ;do
      columnSum=$((columnSum+${line:$i:1}))
    done
    if $(awk "BEGIN {exit !(${columnSum} >= ${#carbonDioxideRate[@]}/2)}") ;then
      bitRating=0
    else
      bitRating=1
    fi
    for ((j=0;j<${#carbonDioxideRate[@]};j++)) ;do
      if [ ${bitRating} -eq ${carbonDioxideRate[$j]:$i:1} ];then
        tempCarbonDioxideRate+=(${carbonDioxideRate[$j]})
      fi
    done
    carbonDioxideRate=(${tempCarbonDioxideRate[@]})
  fi
done
hexOxygenRateRate=$((2#${oxygenRate}))
hexCarbonDioxideRate=$((2#${carbonDioxideRate}))
printf "%s%s\n" "${dayLabel}-puzzle1 --> Results: $((hexOxygenRateRate*hexCarbonDioxideRate)) (oxygenRate=${oxygenRate}-->${hexOxygenRateRate}|carbonDioxideRate=${carbonDioxideRate}-->${hexCarbonDioxideRate})"