#### rabbitmq消息接收确认（ACK）
<pre>
    listener:
      simple:
        # auto 自动检测异常或者超时事件，如果发生则返回noack，消息自动回到队尾，但是这种方式可能出现消息体本身有问题，返回队尾其他队列也不能消费，造成队列阻塞。
        # manual 手动回调，在程序中我们可以对消息异常记性捕获，如果出现消息体格式错误问题，手动回复ack，接着再次调用发送接口把消息推到队尾。ps:后面还需要错误消息堆积问题~~~
        # NONE 可以称之为自动回调，即使无响应或者发生异常均会通知队列消费成功，会丢失数据。
        acknowledge-mode: manual
</pre>


#### rabbitmq生产者的消息确认(Publisher Confirms and Returns)

<p>
通过Publisher Confirms and Returns机制，生产者可以判断消息是否发送到了exchange及queue，而通过消费者确认机制，Rabbitmq可以决定是否重发消息给消费者，以保证消息被处理。
</p>
<pre>
    #配置文件更改
    publisher-returns: true
    publisher-confirms: true

    /**
     * @author only-lilei
     * @create 2018-06-22 下午2:25
     * 使用场景：
     *
     * 如果消息没有到exchange,则confirm回调,ack=false
     * 如果消息到达exchange,则confirm回调,ack=true
     * exchange到queue成功,则不回调return
     * exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)
     **/
    @Component
    public class TestCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
        @Autowired
        private RabbitTemplate rabbitTemplate;
        @PostConstruct
        public void init() {
            rabbitTemplate.setConfirmCallback(this);
            rabbitTemplate.setReturnCallback(this);
        }
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            if (b) {
                System.out.println("消息发送成功:" + correlationData);
            } else {
                System.out.println("消息发送失败:" + s);
            }
    
        }
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println(message.toString() + " 发送失败");
        }
    }

</pre>