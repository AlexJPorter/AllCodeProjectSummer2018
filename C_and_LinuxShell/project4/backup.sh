#!/bin/bash
#Add the help option
#Make sure everything is executed in order
if [ $# -lt 1 ]; then
	echo "The usage of this command is : backup [options] source-files"
fi
[ ! -d ~/.backup ] && mkdir ~/.backup
#copy files into the backup folder
#if it is not a file then do nothing
for argin in $*
do
	if [ -f $argin ]; then
		cp $argin ~/.backup
	
	elif [ -d $argin ]; then
		cp -r $argin ~/.backup
	
	elif [[ ! $argin == -* ]]; then
		echo "$argin is not a file."
	
	fi
done
helpme=0
for optin in $*
do
	if [ $optin = "-n" ]; then
		echo "Number of files in .backup is:"
		ls ~/.backup | wc -l
		echo "The storage consumed by .backup is"
		ls -l -s ~/.backup
	elif [ $optin = "-l" ]; then
		echo "Files/Directories in .backup:"
		ls -R -l ~/.backup
	elif [ $optin = "-nl" ]; then
		echo "Number of files in .backpu is:"
		ls ~/.backup |wc -l
		echo "The storage consumed by .backup is:"
		ls -l -s ~/.backup
		echo "\n"
		echo "Files/Directories in .backup:"
		ls -R -l ~/.backup
	elif [ $optin = "-ln" ]; then
		echo "Files/Directories in .backup:"
		ls -R -l ~/.backup
		echo "\n"
		echo "Number of files in .backup is:"
		ls ~/.backup |wc -l
		echo "The storage consumed by .backpu is"
		ls -l -s ~/.backup
	elif [ $optin = "--help" ]; then
		helpme=1
	elif [[ $optin == -* ]]; then
		echo "$optin is not a valid option."
	fi

done
if [ $helpme -eq 1 ]; then
	echo "This program copies files to another directory called .backup in the home directory"
	echo "Use the option '-l'to display all the files/directories in backup."
	echo "Use the option '-n' to display the number of files in backup and the memory they take up."
	echo "List options and files in any order, but files will be copied first and then options are displayed."

fi
