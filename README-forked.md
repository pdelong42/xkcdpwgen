Note from the forker
====================

I snagged this because:

a) it seemed like a handy utility, and should only take an afternoon to digest; and

b) it seemed like a good porting project for my ongoing efforts to learn Clojure.

There are a few significant differences between my code and the original:

 - I made no restriction on the range of word lengths.  All words in
   the source list are included.  Since this includes words less than
   three characters long, and all words are counted the same, this may
   be a problem.  I intend to rectifty this later by calculating the
   entropy based on number of characters instead of number of words.

 - Maximum word length - used for alignment of output columns - is not
   hard-coded at the largest allowed word length, but is instead
   derived from the maximum word length found in the source list.
   This currently results in output with too much space between
   columns, in-general.  I intend to fix this later by only accounting
   for words that are actually picked.
