#!/bin/bash
IFS=$'\n'
dayNumber=2
dayLabel="DAY${dayNumber}"
scriptDir="$(dirname "$0")"
inputName="input${dayNumber}"
inputArray=($(cat ${scriptDir}/${inputName}))
depth=0
horizontalPosition=0
for line in ${inputArray[@]} ;do
  action=${line%% *}
  value=${line##* }
  case ${action} in
    "forward")
      horizontalPosition=$((horizontalPosition+value))
    ;;
    "down")
      depth=$((depth+value))
    ;;
    "up")
      depth=$((depth-value))
    ;;
  esac
done
printf "%s%s\n" "${dayLabel}-puzzle1 --> Results: $((depth*horizontalPosition)) (horizontalPosition=${horizontalPosition}|depth=${depth})"

depth=0
horizontalPosition=0
aim=0
for line in ${inputArray[@]} ;do
  action=${line%% *}
  value=${line##* }
  case ${action} in
    "forward")
      horizontalPosition=$((horizontalPosition+value))
      depth=$((depth+(aim*value)))
    ;;
    "down")
      aim=$((aim+value))
    ;;
    "up")
      aim=$((aim-value))
    ;;
  esac
done
printf "%s%s\n" "${dayLabel}-puzzle2 --> Results: $((depth*horizontalPosition)) (horizontalPosition=${horizontalPosition}|depth=${depth}|aim=${aim})"