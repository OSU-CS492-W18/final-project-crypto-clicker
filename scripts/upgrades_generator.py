import random
import xml.etree.ElementTree as ET

#collect names from file
names = []
with open("upgrade_names.txt", 'r') as f:
    for line in f:
        names.append(line[:-1])

#shuffle name order cause why not
random.shuffle(names)

num = 1
amount = 50
cost = 5.0
root = ET.Element('upgrades')
#with open("upgrades.xml", 'w+') as f:
for name in names:
    upgrade = ET.SubElement(root, 'upgrade')

    nameTag = ET.SubElement(upgrade, 'name')
    nameTag.text = name

    amountTag = ET.SubElement(upgrade, 'amount')
    amountTag.text = str(amount)
    amount = amount + 25

    costTag = ET.SubElement(upgrade, 'cost')
    costTag.text = str(cost)
    cost = cost*(1 + float(amount)/100.0)

    countTag = ET.SubElement(upgrade, 'count')
    countTag.text = str(num)
    num += 1

tree = ET.ElementTree(root)
tree.write("upgrades.xml")
