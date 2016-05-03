# Design Decisions

This is where we document all the major decisions made in the development process.

[Scales](#scales)

## Scales (How far do we go?)<a name="scales"></a>

The decision has been made to only accept scales with one sharp or flat in the name.
For example: E# major is fine, but Fbb major is not.

The main reason for this is that some scales that have double flats or double sharps in
the name will require triple sharps or triple flats as output. This is not something that 
our program will accept.

Details of the correspondence can be seen below:
>Hi Moffat,
>
>I have a question related to music theory (sorry!) and the scale command.
>
>In general, for other commands we can accept any enharmonic up to double sharps and double flats - so semitone up Ebb is totally fine. However scales are slightly different. If we accept double sharps or double flats  (e.g scale Ebb major) then some results will need triple sharps or triple flats. This doesn't really make sense in music theory.
>
>Usually in music theory only scales in the 'circle of fifths' are used, however in theory the circle is really an infinite spiral. There are some exceptions where other theoretical scales may be used, such as G# major.
>
>My suggestion to deal with this is to not accept any double flats or double sharps as input for the scale command. An alternative could be to return the base enharmonic scale (so Ebb major would give D major) but I feel like this doesn't make sense because even though they sound the same, they are not the same scale.
>
>Let me know what you think,
>Isabelle

And his reply:

>Hi Isabelle,
> 
>Thank you for the detailed explanation. It made things easier to understand.
> 
>We will not need scales to accept double flats or double sharps (i.e. your first suggestion). Could you please record this (and other such decisions) into your design documentation file? Perhaps you can have a link to this email for context. We can then revisit it if necessary and do your second suggestion – however, we won’t for now.
> 
>Have a good weekend!
>Moffat



## The decision was made to not count partial marks in the musical terms tutor

The decision was decided on the 27th of april during a meeting with Moffat, when he said that he did not want the user to receive partial marks for questions in the musical terms tutor.





