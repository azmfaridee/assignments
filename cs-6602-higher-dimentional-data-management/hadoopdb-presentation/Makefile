LATEX = pdflatex
BIBTEX = bibtex
MAIN = hadoopdb-presentations

all:	$(MAIN)
hadoopdb-presentations: $(MAIN).tex $(MAIN).bib
	$(LATEX) $(MAIN)
#	$(BIBTEX) $(MAIN)
	$(LATEX) $(MAIN)
#	$(LATEX) $(MAIN)
	open $(MAIN).pdf

clean:
	rm -f $(MAIN).dvi $(MAIN).pdf $(MAIN).bbl $(MAIN).aux $(MAIN).log $(MAIN).blg $(MAIN).rel $(MAIN).nav $(MAIN).out $(MAIN).snm $(MAIN).toc
