See http://xkcd.com/936/

Basic argument is that picking 4 random common words for a password gives you
about 44 bits of entropy, vs, say 28 bits of entropy for a "strong" (but
patterned to be kinda memorable) random password.

xkcdpwgen (should) let you specify how strong a password you want (either in
bits of entropy, or as equivalent to a random string of printable ascii
characters of some given length) and generates candidate passwords as a list of
words you should concatenate together.

It defaults to 44 bits of entropy as per the cartoon.
