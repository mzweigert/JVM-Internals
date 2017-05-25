#!/usr/bin/env bash
COUNT=$1
TEMPLATE_CLASS="DummyClass0.java"
get_class_body(){
	echo $(sed "s/DummyClass0/DummyClass$1/" ${TEMPLATE_CLASS})
}
for i in $(seq 1 $COUNT); do get_class_body $i > "DummyClass$i.java"
done
