<template>
  <div id="content1234">
    <p style="text-align: center;">
      <span style="font-size: 36px;">dfdfdfdf</span><br/>
    </p>
    <pre class="brush:cpp;toolbar:false">#include&lt;iostream&gt;
using namespace std;
int main(){
    cout &lt;&lt; &quot;Hello World&quot;;
}</pre>
    <pre class="brush:java;toolbar:false">public&nbsp;Test{
&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;static&nbsp;void&nbsp;main(String&nbsp;args[]){
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;SDSD&quot;);
&nbsp;&nbsp;&nbsp;&nbsp;}
}</pre><p><br/></p>
    <pre class="brush:java;toolbar:false">public&nbsp;Test{
&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;static&nbsp;void&nbsp;main(String&nbsp;args[]){
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;SDSD&quot;);
&nbsp;&nbsp;&nbsp;&nbsp;}
}</pre><p><br/></p>
    <pre class="brush:java;toolbar:false">public&nbsp;Test{
&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;static&nbsp;void&nbsp;main(String&nbsp;args[]){
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;SDSD&quot;);
&nbsp;&nbsp;&nbsp;&nbsp;}
}</pre><p><br/></p>
    <pre class="brush:java;toolbar:false">public&nbsp;Test{
&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;static&nbsp;void&nbsp;main(String&nbsp;args[]){
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(&quot;SDSD&quot;);
&nbsp;&nbsp;&nbsp;&nbsp;}
}</pre><p><br/></p>
    <ol class=" list-paddingleft-2" style="list-style-type: upper-alpha;">
      <li>
        <p>
          df
      </p>
      </li>
      <li>
        <p>
          dfd
      </p>
      </li>
      <li>
        <p>
          fdfd
      </p>
      </li>
      <li>
        <p>
          fdfdf<br/>
        </p>
      </li>
    </ol>
  </div>
</template>
<style>

</style>
<script>
  import '../../static/UE/ueditor.parse'
  export default {
    name: 'showContent',
    mounted () {
      window.uParse('#content1234', {
        rootPath: '../../static/UE/'
      })
    }
  }
</script>
