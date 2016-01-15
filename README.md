# recycler
recycle messages from the error queue back to the input until they fail too much

# Design
- a single recycler instance handls errors from a single step
-- a step can be a source (for dead lettering, set retries to 0), a sink or a 
   dataflow job. 
- the recycler will accept messages (presumably from an error topic but doesn't
  have to be), increment or set the error_count value in the message envelope
- if error_counter meets or exceeds the threshold, the message is written to 
  the dead letter location for processing by something else
- else write the message to the original "input" topic
- if a message has been seen again in under 5 minutes (default), pause and wait
  for full retry gap.  prevents short lived, transient network errors from 
  flushing large amounts of messages down the quickly retried toilet
