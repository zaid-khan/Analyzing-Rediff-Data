import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Ngram {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      String curr = new String();
      String ngram = new String();
      int ng = 5;
      int n = ng-1;
      String prev_Tokens[] = new String[n];
      Boolean notNull = true;
      
      while (itr.hasMoreTokens()) {
        curr = itr.nextToken();
        for(int i = 0;i < n;++i){
          if( prev_Tokens[i] == null && prev_Tokens[i] == ""){
            notNull = false;
            break;
          }
        }
        if( notNull){
          for(int i = 0; i < n; i++){
            ngram += " "+prev_Tokens[i];
          }
        }
        ngram += " "+curr;
        word.set(ngram);
        context.write(word, one);
        ngram = "";
        for(int i = 0; i < n-1; ++i){
          prev_Tokens[i] = prev_Tokens[i+1];
        }
        prev_Tokens[n-1] = curr;
        notNull = true;
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      if(sum > 1){
        result.set(sum);
        context.write(key, result);
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(Ngram.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}