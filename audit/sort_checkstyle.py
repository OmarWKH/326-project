import re

def comp_fn(line):
	match = re.search(r'\[.*\] \\appCruise\\([a-zA-Z]*)\.java:([0-9]+:?[0-9]*):.*\[([a-zA-Z]*)]', line)

	if match:
		file = match.group(1)
		position = match.group(2)
		category = match.group(3)
		return file+category+position
	else:
		return '0'

if __name__ == '__main__':
	audit = open('audit.txt', 'r')
	sorted_audit = open('sorted_audit.txt', 'w')

	lines = audit.readlines()
	sorted_lines = sorted(lines, key=comp_fn)

	for line in sorted_lines:
		sorted_audit.write(line+'\n')

	audit.close()
	sorted_audit.close()
